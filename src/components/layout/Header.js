import React from 'react';
import { useNavigate } from 'react-router-dom';

const Header = () => {
    const navigate = useNavigate();

    const handleLogout = () => {
        // Например, удалить токен из localStorage и редирект на логин
        localStorage.removeItem('token');
        navigate('/');
    };

    const goToApplications = () => {
        navigate('/applications'); // <- сюда ведёт список заявлений
    };

    return (
        <header style={styles.header}>
            <div style={styles.left} onClick={goToApplications}>
                Заявления
            </div>
            <button style={styles.logoutButton} onClick={handleLogout}>
                Выйти
            </button>
        </header>
    );
};

const styles = {
    header: {
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center',
        padding: '10px 20px',
        backgroundColor: '#007bff',
        color: '#fff',
        fontWeight: '600',
        fontSize: '18px',
        cursor: 'default',
    },
    left: {
        cursor: 'pointer',
        userSelect: 'none',
    },
    logoutButton: {
        backgroundColor: '#0056b3',
        border: 'none',
        color: 'white',
        padding: '8px 16px',
        borderRadius: '5px',
        cursor: 'pointer',
        fontWeight: '600',
        fontSize: '14px',
    }
};

export default Header;
