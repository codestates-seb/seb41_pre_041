import axios from "axios";
import { useState } from "react";
import { Link } from "react-router-dom";

const LoginForm = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [emailWarning, setEmailWarning] = useState("");
  const [passwordWarning, setPasswordWarning] = useState("");
  const emailReg = /[a-z0-9]+@[a-z]+.[a-z]{2,6}/;
  const pwdReg = /^(?=.*[a-zA-Z])((?=.*\d)(?=.*\W)).{8,20}$/;

  const loginHandler = async (e) => {
    e.preventDefault();
    if (!email.length) {
      setEmailWarning("Email cannot be empty.");
    } else if (!emailReg.test(email)) {
      setEmailWarning("The email is not a valid email address.");
    } else {
      setEmailWarning("");
    }

    if (!password.length) {
      setPasswordWarning("Password cannot be empty.");
    } else if (!pwdReg.test(password)) {
      setPasswordWarning("The password is not a valid password.");
    } else {
      setPasswordWarning("");
    }
    if (
      password.length &&
      email.length &&
      emailReg.test(email) &&
      pwdReg.test(password)
    ) {
      setEmailWarning("");
      setPasswordWarning("");
      await axios
        .post(`${process.env.REACT_APP_API_URL}/api/auths/login`, {
          email,
          password,
        })
        .then((response) => {
          if (response) {
            let accessToken = response.headers.authorization;
            let refreshToken = response.headers.refreshtoken;
            sessionStorage.setItem("accessToken", accessToken);
            sessionStorage.setItem("refreshToken", refreshToken);
            window.location.replace("/questions");
          }
        })
        .catch((error) => {
          console.log(error);
          setEmailWarning("The email or password is incorrect.");
        });
    }
  };

  return (
    <section>
      <form className="account-login-container" onSubmit={loginHandler}>
        <div className="email">
          <label htmlFor="email" className="email-label">
            Email
          </label>
          <div className="email-input">
            <input
              id={emailWarning ? "wrong-email" : "email"}
              type="email"
              onChange={(e) => setEmail(e.target.value)}
              value={email}
            ></input>
            <span className="warning">{emailWarning}</span>
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
            <input
              id={passwordWarning ? "wrong-password" : "password"}
              type="password"
              onChange={(e) => setPassword(e.target.value)}
              value={password}
            ></input>
            <span className="warning">{passwordWarning}</span>
          </div>
        </div>
        <div className="login">
          <button type="submit" className="login-btn">
            Log in
          </button>
        </div>
      </form>
    </section>
  );
};

export default LoginForm;
