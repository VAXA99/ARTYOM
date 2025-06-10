import React, { useState, useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import './Application.css';

const Application = () => {
    const [formData, setFormData] = useState({
        fullName: '',
        position: '',
        department: '',
        startDate: '',
        endDate: '',
        reasonId: '',
        employeeSignature: '',
        fakeECP: '',
        ecpConfirmed: false,
    });

    const [reasons, setReasons] = useState([]);
    const [isPositionReadOnly, setIsPositionReadOnly] = useState(false);
    const [employeeId, setEmployeeId] = useState(null);
    const [loading, setLoading] = useState(false);
    const [signatureDisabled, setSignatureDisabled] = useState(false);

    const navigate = useNavigate();
    const [searchParams] = useSearchParams();
    const applicationId = searchParams.get('id');

    useEffect(() => {
        // Получаем данные сотрудника
        fetch('/api/user/employees/me', {
            headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
        })
            .then(res => res.json())
            .then(data => {
                const fullName = `${data.surname} ${data.name} ${data.middleName}`;
                let position = data.role?.name === 'ROLE_USER' ? 'Рядовой сотрудник' : '';
                setFormData(prev => ({ ...prev, fullName, position }));
                setIsPositionReadOnly(data.role?.name === 'ROLE_USER');
                setEmployeeId(data.id);
            })
            .catch(() => {});

        // Получаем причины отпуска
        fetch('/api/core/application-reasons/', {
            headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
        })
            .then(res => res.json())
            .then(setReasons)
            .catch(() => {});
    }, []);

    useEffect(() => {
        if (!applicationId) return;

        // Загружаем данные заявки
        fetch(`/api/core/applications/${applicationId}`, {
            headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
        })
            .then(res => {
                if (!res.ok) throw new Error('Не удалось загрузить заявку');
                return res.json();
            })
            .then(data => {
                setFormData(prev => ({
                    ...prev,
                    startDate: data.startDate,
                    endDate: data.endDate,
                    reasonId: data.reason?.id || '',
                    department: data.employee.department || '',
                }));

                // Загружаем подпись заявки
                fetch(`/api/core/application-signatures/application/${applicationId}`, {
                    headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
                })
                    .then(res => {
                        if (!res.ok) throw new Error('Не удалось загрузить подпись');
                        return res.json();
                    })
                    .then(signatures => {
                        if (signatures.length > 0) {
                            const sig = signatures[0]; // Предположим, что 1 подпись
                            setFormData(prev => ({
                                ...prev,
                                employeeSignature: sig.signature,
                                fakeECP: sig.signature,
                                ecpConfirmed: true,
                            }));
                            setSignatureDisabled(true); // Запретить менять подпись
                        }
                    })
                    .catch(() => {
                        // Если нет подписи, то разрешаем ставить
                        setSignatureDisabled(false);
                    });
            })
            .catch(err => alert(err.message));
    }, [applicationId]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        if (name === 'position' && isPositionReadOnly) {
            return;
        }
        if ((name === 'employeeSignature' || name === 'fakeECP') && signatureDisabled) {
            return; // запрещаем менять подпись, если дизейбл
        }
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    const signEmployee = () => {
        if (signatureDisabled) return; // если дизейбл — не даем подписывать
        const fakeSign = 'ECP-' + Math.random().toString(36).substr(2, 10).toUpperCase();
        setFormData(prev => ({
            ...prev,
            employeeSignature: fakeSign,
            fakeECP: fakeSign,
            ecpConfirmed: true,
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!formData.ecpConfirmed) {
            alert('Пожалуйста, подпишите заявку, нажав кнопку "Подписать"');
            return;
        }
        if (!employeeId) {
            alert('Ошибка: ID сотрудника не найден');
            return;
        }

        setLoading(true);

        const applicationBody = {
            employee: { id: employeeId },
            startDate: formData.startDate,
            endDate: formData.endDate,
            reason: { id: formData.reasonId },
        };

        try {
            const token = localStorage.getItem('token');
            const url = applicationId ? `/api/core/applications/${applicationId}` : '/api/core/applications/';
            const method = applicationId ? 'PUT' : 'POST';

            // Сохраняем заявку
            const response = await fetch(url, {
                method,
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${token}`,
                },
                body: JSON.stringify(applicationBody),
            });

            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(errorText || 'Ошибка при отправке заявки');
            }

            const savedApplication = await response.json();
            const applicationIdSaved = savedApplication.id;

            // Сохраняем подпись — POST если новая, PUT если редактирование
            const signatureBody = {
                application: { id: applicationIdSaved },
                signingEmployee: { id: employeeId },
                signatureDate: new Date().toISOString().slice(0, 10), // yyyy-mm-dd
                signature: formData.employeeSignature,
            };

            // Проверяем, есть ли уже подпись для заявки
            const sigsRes = await fetch(`/api/core/application-signatures/application/${applicationIdSaved}`, {
                headers: { Authorization: `Bearer ${token}` }
            });
            let existingSignatures = [];
            if (sigsRes.ok) {
                existingSignatures = await sigsRes.json();
            }

            if (existingSignatures.length > 0) {
                // Обновляем первую подпись
                const sigId = existingSignatures[0].id;
                await fetch(`/api/core/application-signatures/${sigId}`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: `Bearer ${token}`,
                    },
                    body: JSON.stringify(signatureBody),
                });
            } else {
                // Создаем новую подпись
                await fetch(`/api/core/application-signatures/`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: `Bearer ${token}`,
                    },
                    body: JSON.stringify(signatureBody),
                });
            }

            alert(applicationId ? 'Заявка успешно обновлена!' : 'Заявка успешно создана!');
            navigate('/applications');

        } catch (error) {
            alert('Ошибка при отправке заявки: ' + error.message);
        } finally {
            setLoading(false);
        }
    };

    return (
        <form className="application-form" onSubmit={handleSubmit}>
            <h2>Заявка на отпуск без сохранения заработной платы</h2>

            <label>ФИО сотрудника:</label>
            <input type="text" name="fullName" value={formData.fullName} readOnly />

            <label>Должность:</label>
            <input
                type="text"
                name="position"
                placeholder="Введите должность"
                value={formData.position}
                onChange={handleChange}
                required
                readOnly={isPositionReadOnly}
                style={isPositionReadOnly ? { backgroundColor: '#e9ecef', cursor: 'not-allowed' } : {}}
            />

            <label>Отдел:</label>
            <input
                type="text"
                name="department"
                placeholder="Введите отдел"
                value={formData.department}
                onChange={handleChange}
                required
            />

            <label>Дата начала отпуска:</label>
            <input
                type="date"
                name="startDate"
                value={formData.startDate}
                onChange={handleChange}
                required
            />

            <label>Дата окончания отпуска:</label>
            <input
                type="date"
                name="endDate"
                value={formData.endDate}
                onChange={handleChange}
                required
            />

            <label>Причина отпуска без сохранения заработной платы:</label>
            <select
                name="reasonId"
                value={formData.reasonId}
                onChange={handleChange}
                required
            >
                <option value="" disabled>Выберите причину</option>
                {reasons.map(reason => (
                    <option key={reason.id} value={reason.id}>{reason.name}</option>
                ))}
            </select>

            <div style={{ marginBottom: '20px' }}>
                <button
                    type="button"
                    onClick={signEmployee}
                    style={{
                        width: '100%',
                        padding: '10px 15px',
                        cursor: signatureDisabled ? 'not-allowed' : 'pointer',
                        backgroundColor: signatureDisabled ? '#6c757d' : '#007bff',
                        color: '#fff',
                        border: 'none',
                        borderRadius: '6px',
                    }}
                    disabled={loading || signatureDisabled}
                >
                    Подписать
                </button>
            </div>

            <label>Электронная подпись:</label>
            <input
                type="text"
                name="fakeECP"
                value={formData.fakeECP}
                readOnly
                style={{ marginBottom: '20px' }}
            />

            <button type="submit" disabled={loading}>
                {loading ? 'Отправка...' : (applicationId ? 'Обновить заявку' : 'Отправить заявку')}
            </button>
        </form>
    );
};

export default Application;
