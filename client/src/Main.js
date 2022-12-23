import React from 'react';
import styled from "styled-components";
import { Routes, Route } from "react-router-dom";
import Question from './pages/Question';
import Questions from './pages/Questions';
import EditAnswer from './pages/EditAnswer';
import EditQuestion from './pages/EditQuestion';
import Footer from './components/Footer';
import LeftSideBar from './components/LeftSideBar';

const SectionWrap = styled.div`
    display: flex;
    justify-content: center;
    width: 100%;
`

const SectionContainer = styled.div`
    display: flex;
    width: 1264px;
    min-width: 1264px;
`

const Main = () => {
    return (
        <>
        <SectionWrap>
            <SectionContainer>
         <LeftSideBar/>
            <Routes>
                <Route path="/" element={<Questions />} />
                <Route path="/question" element={<Question />} />
                <Route path="/edit/answer" element={<EditAnswer />} />
                <Route path="/edit/question" element={<EditQuestion />} />
            </Routes>
            </SectionContainer>
            </SectionWrap>
         <Footer/>
    </>
    );
};

export default Main;