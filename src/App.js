import React from 'react';
import { BrowserRouter as Router, Routes, Route, Outlet } from 'react-router-dom';
import Login from "./components/auth/Login";
import SignUp from "./components/auth/SignUp";  // импорт регистрация
import Application from "./components/applications/Application";
import Header from "./components/layout/Header";
import ApplicationList from "./components/applications/ApplicationList";

const LayoutWithHeader = () => {
    return (
        <>
            <Header />
            <Outlet />
        </>
    );
};

function App() {
    return (
        <Router>
            <Routes>
                {/* Страницы без Header */}
                <Route path="/" element={<Login />} />
                <Route path="/register" element={<SignUp />} />

                {/* Все остальные страницы с Header */}
                <Route element={<LayoutWithHeader />}>
                    <Route path="/applications" element={<ApplicationList />} />
                    <Route path="/application" element={<Application />} />
                </Route>
            </Routes>
        </Router>
    );
}

export default App;
