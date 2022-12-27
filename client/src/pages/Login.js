import styled from "styled-components";
import LoginView from "../components/Login/LoginView";
import LoginContainer from "../components/Login/LoginContainer";
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
      <LoginContainer>
        <LoginView />
      </LoginContainer>
    </LoginArticle>
  );
};

export default Login;
