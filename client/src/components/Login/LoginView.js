import { Link } from "react-router-dom";
import styled from "styled-components";
import { ReactComponent as Google } from "../../assets/google-icon.svg";
import { ReactComponent as Github } from "../../assets/github-icon.svg";
import { ReactComponent as Facebook } from "../../assets/facebook-icon.svg";

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
    height: 300px;
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
      height: 38px;
      margin-top: 7px;
      &:focus {
        border: 1px solid #92c3e9;
        border-radius: 5px;
        box-shadow: 0px 0px 0px 5px #d9eaf7;
        outline: 0;
      }
    }

    & button {
      width: 290px;
      height: 50px;
      margin: 8px 0;
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

const LoginView = () => {
  return (
    <LoginContainer>
      {/*로그인 폼 로고*/}
      <div className="login-logo">
        <Link to="/">
          <svg width="45" height="45" viewBox="0 0 32 37">
            <path d="M26 33v-9h4v13H0V24h4v9h22Z" fill="#BCBBBB"></path>
            <path
              d="m21.5 0-2.7 2 9.9 13.3 2.7-2L21.5 0ZM26 18.4 13.3 7.8l2.1-2.5 12.7 10.6-2.1 2.5ZM9.1 15.2l15 7 1.4-3-15-7-1.4 3Zm14 10.79.68-2.95-16.1-3.35L7 23l16.1 2.99ZM23 30H7v-3h16v3Z"
              fill="#F48024"
            ></path>
          </svg>
        </Link>
      </div>
      {/*소셜 로그인 폼*/}
      <section className="social-login-container">
        <button className="google">
          <Google />
          <span>Log in with Google</span>
        </button>
        <button className="github">
          <Github />
          <span>Log in with Github</span>
        </button>
        <button className="facebook">
          <Facebook />
          <span>Log in with Facebook</span>
        </button>
      </section>
      {/*이메일 비밀번호 입력창*/}
      <section className="account-login-container">
        <div className="email">
          <label htmlFor="email" className="email-label">
            Email
          </label>
          <div className="email-input">
            <input id="email"></input>
          </div>
        </div>
        <div className="password">
          <label htmlFor="password" className="password-label">
            Password
            <Link to="/notfound" className="talent-link">
              <span>Forgot password?</span>
            </Link>
          </label>
          <div className="password-input">
            <input id="password"></input>
          </div>
        </div>
        <div className="login">
          <button className="login-btn">Log in</button>
        </div>
      </section>
      {/*부가 파트*/}
      <section className="part">
        <div className="signup-part">
          <span>Don’t have an account? </span>
          <Link className="signup-link" to="/signup">
            Sign up
          </Link>
        </div>
        <div className="talent-part">
          <span>Are you an employer? </span>
          <Link to="/notfound" className="talent-link">
            <span>Sign up on Talent</span>
          </Link>
        </div>
      </section>
    </LoginContainer>
  );
};
export default LoginView;
