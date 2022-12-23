import styled from "styled-components";
import LoginView from "../components/Login/LoginView";

const LoginArticle = styled.article`
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f1f2f3;
`;

const Login = () => {
  return (
    <LoginArticle>
      <LoginView />
    </LoginArticle>
  );
};

export default Login;
