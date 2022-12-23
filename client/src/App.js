import React from 'react';
import styled from "styled-components";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { createGlobalStyle } from 'styled-components';
import Header from "./components/Header";
import Main from './Main';
import Signup from './pages/Signup';
import Login from './pages/Login';
import Logout from './pages/Logout';
import Ask from './pages/Ask';
import NotFound from './components/NotFound';

// CSS 초기화
const GlobalStyle = createGlobalStyle`
  *{
    margin: 0;
    padding: 0;
    box-sizing: border-box;
    list-style: none;
    text-decoration: none;
    font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
  }
`
const SectionWrap = styled.div`
    width: 100%;
    padding-top: 53px;
`

function App() {
  return (
    <>
    <Router>
      <GlobalStyle />
      <Header />
      <SectionWrap>
      <Routes>
        <Route path='/signup' element={<Signup/>}/>
        <Route path='/login' element={<Login/>}/>
        <Route path='/logout' element={<Logout/>}/>
        <Route path='/ask' element={<Ask/>}/>
        <Route path='/*' element={<Main/>}/>
        <Route path='/notfound' element={<NotFound/>}/>
      </Routes>
      </SectionWrap>
    </Router>
    </>
    )
};

export default App;
