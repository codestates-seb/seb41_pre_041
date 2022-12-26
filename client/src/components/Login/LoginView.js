import { Link } from "react-router-dom";
import { ReactComponent as Google } from "../../assets/google-icon.svg";
import { ReactComponent as Github } from "../../assets/github-icon.svg";
import { ReactComponent as Facebook } from "../../assets/facebook-icon.svg";
import LoginForm from "./LoginForm";

const LoginView = () => {
  return (
    <>
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
      <LoginForm />
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
    </>
  );
};
export default LoginView;
