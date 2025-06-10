import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './SignUp.css';

const SignUp = () => {
    const [formData, setFormData] = useState({
        surname: '',
        name: '',
        middleName: '',
        username: '',
        email: '',
        password: '',
        confirmPassword: '',
    });

    const navigate = useNavigate();

    const handleChange = e => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async e => {
        e.preventDefault();

        if (formData.password !== formData.confirmPassword) {
            alert('Пароли не совпадают!');
            return;
        }

        try {
            const response = await fetch('/api/auth/register', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    surname: formData.surname,
                    name: formData.name,
                    middleName: formData.middleName,
                    username: formData.username,
                    email: formData.email,
                    password: formData.password,
                }),
            });

            if (!response.ok) {
                alert('Ошибка регистрации');
                return;
            }

            const data = await response.json();
            localStorage.setItem('token', data.token);
            navigate('/applications');
        } catch (err) {
            alert('Ошибка подключения к серверу');
        }
    };

    return (
        <form className="signup-form" onSubmit={handleSubmit}>
            <h2>Регистрация</h2>
            <input
                type="text"
                name="surname"
                placeholder="Фамилия"
                value={formData.surname}
                onChange={handleChange}
                required
            />
            <input
                type="text"
                name="name"
                placeholder="Имя"
                value={formData.name}
                onChange={handleChange}
                required
            />
            <input
                type="text"
                name="middleName"
                placeholder="Отчество"
                value={formData.middleName}
                onChange={handleChange}
                required
            />
            <input
                type="text"
                name="username"
                placeholder="Имя пользователя"
                value={formData.username}
                onChange={handleChange}
                required
            />
            <input
                type="email"
                name="email"
                placeholder="Email"
                value={formData.email}
                onChange={handleChange}
                required
            />
            <input
                type="password"
                name="password"
                placeholder="Пароль"
                value={formData.password}
                onChange={handleChange}
                required
            />
            <input
                type="password"
                name="confirmPassword"
                placeholder="Подтверждение пароля"
                value={formData.confirmPassword}
                onChange={handleChange}
                required
            />
            <button type="submit">Зарегистрироваться</button>
        </form>
    );
};

export default SignUp;
