import axios from "axios";
import { useState } from "react";
import { Link } from "react-router-dom";

const LoginForm = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [emailWarning, setEmailWarning] = useState("");
  const [passwordWarning, setPasswordWarning] = useState("");

  const reg = /[a-z0-9]+@[a-z]+.[a-z]{2,6}/;

  const loginHandler = async () => {
    if (!email.length) {
      setEmailWarning("Email cannot be empty.");
    } else if (!reg.test(email)) {
      setEmailWarning("The email is not a valid email address.");
    } else {
      setEmailWarning("");
    }
    if (!password.length) {
      setPasswordWarning("Password cannot be empty.");
    } else {
      setPasswordWarning("");
    }
    if (password.length && email.length && reg.test(email)) {
      setEmailWarning("");
      setPasswordWarning("");
      await axios
        .post("/login", {
          email,
          password,
        })
        .then((response) => {
          if (response) {
            let accessToken = response.headers.get("Authorization");
            sessionStorage.setItem("Authorization", accessToken);
            window.location.replace("/");
          }
        })
        .catch((error) => {
          console.log(`ERROR RESPONSE : ${error.status}`);
          setEmailWarning("The email or password is incorrect.");
        });
    }
  };

  return (
    <section className="account-login-container">
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
        <button onClick={loginHandler} className="login-btn">
          Log in
        </button>
      </div>
    </section>
  );
};

export default LoginForm;
