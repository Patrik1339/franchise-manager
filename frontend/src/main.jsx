import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import App from './App.jsx'
import UserHome from "./pages/user/UserHome.jsx";
import AdminHome from "./pages/admin/AdminHome.jsx";
import FranchisesPage from "./pages/franchise/FranchisesPage.jsx";

createRoot(document.getElementById('root')).render(
  <StrictMode>
      <BrowserRouter>
          <Routes>
              <Route path="/" element={<App/>}/>
              <Route path="/user-home" element={<UserHome/>}/>
              <Route path="/franchises" element={<FranchisesPage/>}/>
              <Route path="/profile" element={<UserHome/>}/>
              <Route path="/admin-home" element={<AdminHome/>}/>
          </Routes>
      </BrowserRouter>
  </StrictMode>,
)