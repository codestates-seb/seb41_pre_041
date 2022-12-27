import styled from "styled-components";

const LoginContainer = styled.section`
  width: 400px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  .login-logo {
    margin: 20px 0;
  }
  .social-login-container {
    width: 350px;
    margin-bottom: 20px;
    display: flex;
    flex-direction: column;
  }
  .social-login-container > button {
    margin: 4px 0px;
    padding: 14px;
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 16px;
    font-weight: 500;
    border-radius: 5px;
    text-decoration: none;
    cursor: pointer;
    > span {
      margin-left: 6px;
    }
  }

  .google {
    color: #2f3337;
    background-color: #ffffff;
    border: 1px solid #a7a7a7;
    &:hover {
      background-color: #f5f5f5;
      color: #2f3337;
    }
  }
  .github {
    color: #ffffff;
    background-color: #2f3337;
    border-color: transparent;
    &:hover {
      background-color: #000000;
      color: #ffffff;
    }
  }
  .facebook {
    color: #ffffff;
    background-color: #385499;
    border-color: transparent;
    &:hover {
      background-color: #364984;
      color: #ffffff;
    }
  }

  .account-login-container {
    width: 350px;
    height: 100%;
    margin-bottom: 30px;
    padding: 30px 30px;
    border-radius: 10px;
    box-shadow: 0px 0px 10px #a7a7a7;
    background-color: #ffffff;
    > div {
      margin-bottom: 25px;
      font-size: 20px;
      font-weight: 600;
    }
    & input {
      width: 290px;
      height: 44px;
      margin-top: 7px;
      padding: 10px;
      font-size: 16px;
      &:focus {
        border: 1px solid #92c3e9;
        border-radius: 5px;
        box-shadow: 0px 0px 0px 5px #d9eaf7;
        outline: 0;
      }
    }
    & #wrong-email,
    #wrong-password {
      border: 1px solid #de4f54;
      &:focus {
        border: 1px solid #de4f54;
        box-shadow: 0px 0px 0px 5px #f6e0e0;
      }
    }

    .login {
      margin-bottom: 10px;
    }

    & button {
      width: 290px;
      height: 50px;
      border: none;
      border-radius: 3px;
      background-color: #0a95ff;
      font-size: 19px;
      color: #ffffff;
      :hover {
        cursor: pointer;
        background-color: #0074cc;
      }
    }
  }

  .password-label > a {
    margin-left: 73px;
    font-size: 15px;
    font-weight: 500;
    color: #0b79ce;
    :hover {
      color: #4da4de;
      cursor: pointer;
    }
  }

  .warning {
    font-size: 13px;
    font-weight: 400;
    color: #c22e32;
  }
  .part {
    text-align: center;
    > div {
      margin-bottom: 10px;
      font-size: 17px;
    }
    & a {
      font-weight: 500;
      color: #0b79ce;
      :hover {
        color: #4da4de;
        cursor: pointer;
      }
    }
  }
`;
export default LoginContainer;
