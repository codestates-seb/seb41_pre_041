import React from "react";
import styled from "styled-components";
import { Routes, Route } from "react-router-dom";
import Question from "./pages/Question";
import Questions from "./pages/Questions";
import EditAnswer from "./pages/EditAnswer";
import EditQuestion from "./pages/EditQuestion";
import Footer from "./components/Footer";
import LeftSideBar from "./components/LeftSideBar";
import NotFound from "./pages/NotFound";

const SectionWrap = styled.div`
  display: flex;
  justify-content: center;
  width: 100%;
`;

const SectionContainer = styled.div`
  display: flex;
  width: 1264px;
  min-width: 1264px;
  min-height: 950px;
  overflow: auto;
`;

const Main = ({ isLogin }) => {
  return (
    <>
      <SectionWrap>
        <SectionContainer>
          <LeftSideBar />
          <Routes>
            <Route path="/questions" element={<Questions />} />
            <Route
              path="/question/:id"
              element={<Question isLogin={isLogin} />}
            />
            <Route
              path="/edit/answer/:id"
              element={isLogin ? <EditAnswer /> : <NotFound />}
            />
            <Route
              path="/edit/question/:id"
              element={isLogin ? <EditQuestion /> : <NotFound />}
            />
            <Route path="/*" element={<NotFound />} />
          </Routes>
        </SectionContainer>
      </SectionWrap>
      <Footer />
    </>
  );
};

export default Main;
