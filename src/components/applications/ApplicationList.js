import React, { useEffect, useState, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import './ApplicationList.css';

const CreateIcon = () => (
    <svg
        xmlns="http://www.w3.org/2000/svg"
        height="20"
        viewBox="0 0 24 24"
        width="20"
        fill="white"
        style={{ marginRight: '8px' }}
    >
        <path d="M19 13H13V19H11V13H5V11H11V5H13V11H19V13Z" />
    </svg>
);

const DotsMenu = ({ onEdit, onDelete }) => {
    const [open, setOpen] = useState(false);
    const ref = useRef();

    useEffect(() => {
        const handleClickOutside = (event) => {
            if (ref.current && !ref.current.contains(event.target)) {
                setOpen(false);
            }
        };
        document.addEventListener('mousedown', handleClickOutside);
        return () => document.removeEventListener('mousedown', handleClickOutside);
    }, []);

    return (
        <div className="dots-menu" ref={ref}>
            <div className="dots" onClick={() => setOpen(!open)}>⋮</div>
            {open && (
                <div className="dropdown">
                    <button onClick={() => { setOpen(false); onEdit(); }}>Редактировать</button>
                    <button className="delete" onClick={() => { setOpen(false); onDelete(); }}>Удалить</button>
                </div>
            )}
        </div>
    );
};

const ApplicationList = () => {
    const [applications, setApplications] = useState([]);
    const [statusesMap, setStatusesMap] = useState({});
    const [employeeId, setEmployeeId] = useState(null);
    const navigate = useNavigate();

    const token = localStorage.getItem('token');

    const getRoleName = (roleName) => {
        if (roleName === 'ROLE_USER') return 'Рядовой сотрудник';
        return roleName || '—';
    };

    // Получаем ID текущего сотрудника
    useEffect(() => {
        if (!token) {
            console.warn('No token found');
            return;
        }

        fetch('/api/user/employees/me', {
            headers: { Authorization: `Bearer ${token}` }
        })
            .then(res => {
                if (!res.ok) throw new Error('Failed to fetch employee data');
                return res.json();
            })
            .then(data => {
                console.log('Current employee data:', data);
                if (data && typeof data.id === 'number') {
                    setEmployeeId(data.id);
                } else {
                    console.error('Employee ID missing or invalid:', data);
                }
            })
            .catch(error => {
                console.error('Error fetching current employee:', error);
            });
    }, [token]);

    // Новый эффект - получение всех заявлений
    useEffect(() => {
        if (!token) return;

        fetch('/api/core/applications/', {
            headers: { Authorization: `Bearer ${token}` }
        })
            .then(res => {
                if (!res.ok) throw new Error('Failed to fetch applications');
                return res.json();
            })
            .then(data => {
                console.log('All applications data:', data);
                setApplications(data);
            })
            .catch(error => {
                console.error('Error fetching all applications:', error);
            });
    }, [token]);

    // Получаем заявления текущего сотрудника
    // useEffect(() => {
    //     if (!employeeId || typeof employeeId !== 'number') {
    //         console.log('employeeId not set yet:', employeeId);
    //         return;
    //     }
    //
    //     fetch(`/api/core/applications/employee/${employeeId}`, {
    //         headers: { Authorization: `Bearer ${token}` }
    //     })
    //         .then(res => {
    //             if (!res.ok) throw new Error('Failed to fetch applications');
    //             return res.json();
    //         })
    //         .then(data => {
    //             console.log('Applications data:', data);
    //             setApplications(data);
    //         })
    //         .catch(error => {
    //             console.error('Error fetching applications:', error);
    //         });
    // }, [employeeId, token]);

    // Загружаем историю статусов для каждой заявки
    useEffect(() => {
        applications.forEach(app => {
            fetch(`/api/core/application-statuses/${app.id}/status-history`, {
                headers: { Authorization: `Bearer ${token}` }
            })
                .then(res => res.json())
                .then(history => {
                    setStatusesMap(prev => ({ ...prev, [app.id]: history }));
                })
                .catch(() => {});
        });
    }, [applications, token]);

    const handleDelete = async (id) => {
        if (!window.confirm('Вы уверены, что хотите удалить это заявление?')) return;

        try {
            const response = await fetch(`/api/core/applications/${id}`, {
                method: 'DELETE',
                headers: { Authorization: `Bearer ${token}` }
            });
            if (response.ok) {
                alert('Заявление успешно удалено');
                setApplications(prev => prev.filter(app => app.id !== id));
            } else {
                alert('Ошибка при удалении заявления');
            }
        } catch {
            alert('Ошибка при удалении заявления');
        }
    };

    const handleEdit = (id) => {
        navigate(`/application?id=${id}`);
    };

    return (
        <div className="application-list-wrapper">
            <button
                className="create-button"
                onClick={() => navigate('/application')}
            >
                <CreateIcon />
                Создать новое заявление
            </button>

            <div className="applications-grid">
                {applications.map(app => (
                    <div className="application-card" key={app.id} style={{ position: 'relative' }}>
                        <DotsMenu
                            onEdit={() => handleEdit(app.id)}
                            onDelete={() => handleDelete(app.id)}
                        />

                        <h3>Заявление на отпуск</h3>
                        <p><b>Подал заявление:</b> {app.employee.surname} {app.employee.name} {app.employee.middleName}</p>
                        <p><b>Должность:</b> {getRoleName(app.employee.role?.name)}</p>
                        <p><b>Отдел:</b> {app.employee.department || '—'}</p>

                        <p><b>Дата начала отпуска:</b> {app.startDate}</p>
                        <p><b>Дата окончания отпуска:</b> {app.endDate}</p>
                        <p><b>Причина:</b> {app.reason ? app.reason.name : '—'}</p>

                        <div className="status-history">
                            <b>История статусов:</b>
                            {statusesMap[app.id] && statusesMap[app.id].length > 0 ? (
                                <ul>
                                    {statusesMap[app.id].map(statusEntry => (
                                        <li key={`${statusEntry.status.id}-${statusEntry.changeDate}`}>
                                            {statusEntry.status.name} — {new Date(statusEntry.changeDate).toLocaleDateString()}
                                        </li>
                                    ))}
                                </ul>
                            ) : (
                                <p>Статусы не загружены</p>
                            )}
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default ApplicationList;
