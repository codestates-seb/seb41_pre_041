import { useState, useEffect } from "react";
import styled from "styled-components";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { createGlobalStyle } from "styled-components";
import Header from "./components/Header";
import Main from "./Main";
import Signup from "./pages/Signup";
import Login from "./pages/Login";
import Logout from "./pages/Logout";
import Ask from "./pages/Ask";
import Home from "./pages/Home";
import NotFound from "./pages/NotFound";
import { TuiEditor } from "./TuiEditor";

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

  ${TuiEditor}
`;

const SectionWrap = styled.div`
  width: 100%;
  padding-top: 53px;
`;

function App() {
  const [isLogin, setIsLogin] = useState(false);

  useEffect(() => {
    !!sessionStorage.getItem("accessToken")
      ? setIsLogin(true)
      : setIsLogin(false);
  }, []);

  return (
    <>
      <Router>
        <GlobalStyle />
        <Header isLogin={isLogin} />
        <SectionWrap>
          <Routes>
          <Route
              path="/"
              element={<Home />}
            />
            <Route
              path="/signup"
              element={isLogin ? <NotFound /> : <Signup />}
            />
            <Route path="/login" element={isLogin ? <NotFound /> : <Login />} />
            <Route
              path="/logout"
              element={isLogin ? <Logout setIsLogin={setIsLogin} /> : <Login />}
            />
            <Route path="/ask" element={isLogin ? <Ask /> : <Login />} />
            <Route path="/*" element={<Main isLogin={isLogin} />} />
            <Route path="/notfound" element={<NotFound />} />
          </Routes>
        </SectionWrap>
      </Router>
    </>
  );
}

export default App;
