import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Login.css';

const Login = () => {
    const [login, setLogin] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async e => {
        e.preventDefault();

        const isEmail = login.includes('@');

        const requestBody = {
            password,
            ...(isEmail ? { email: login } : { username: login }),
        };

        try {
            const response = await fetch('/api/auth/', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(requestBody)
            });

            if (!response.ok) {
                alert('Ошибка входа');
                return;
            }

            const data = await response.json();
            localStorage.setItem("token", data.token);
            navigate('/applications');

        } catch (err) {
            alert('Ошибка подключения к серверу');
        }
    };

    const goToRegister = () => {
        navigate('/register');  // путь к странице регистрации
    };

    return (
        <form className="login-form" onSubmit={handleSubmit}>
            <h2>Вход</h2>
            <input
                type="text"
                name="login"
                placeholder="Имя пользователя или почта"
                value={login}
                onChange={e => setLogin(e.target.value)}
                required
            />
            <input
                type="password"
                name="password"
                placeholder="Пароль"
                value={password}
                onChange={e => setPassword(e.target.value)}
                required
            />
            <button type="submit">Войти</button>

            <button
                type="button"
                className="register-button"
                onClick={goToRegister}
            >
                Зарегистрироваться
            </button>

            <div className="forgot-password">
                <a href="/reset-password">Забыли пароль?</a>
            </div>
        </form>
    );
};

export default Login;
