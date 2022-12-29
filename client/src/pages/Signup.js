import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import styled from "styled-components";
import axios from "axios";
import { ReactComponent as Google } from "../assets/google-icon.svg";
import { ReactComponent as Github } from "../assets/github-icon.svg";
import { ReactComponent as Facebook } from "../assets/facebook-icon.svg";

const Signup = () => {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  //오류메시지 상태저장
  const [emailMessage, setEmailMessage] = useState("");
  const [passwordMessage, setPasswordMessage] = useState("");

  // 유효성 검사
  const [isEmail, setIsEmail] = useState(false);
  const [isPassword, setIsPassword] = useState(false);

  const onNameHandler = (event) => {
    setName(event.currentTarget.value);
  };
  const onEmailHandler = (event) => {
    const currentEmail = event.currentTarget.value;
    setEmail(currentEmail);
    const emailRegExp = /\S+@\S+\.\S+/;
    if (!emailRegExp.test(currentEmail)) {
      setEmailMessage("Please enter a valid email address.");
      setIsEmail(false);
    } else {
      setEmailMessage("");
      setIsEmail(true);
    }
  };

  const onPasswordHandler = (event) => {
    const currentPwd = event.currentTarget.value;
    setPassword(currentPwd);
    const pwdRegExp = /^(?=.*[a-zA-Z])((?=.*\d)(?=.*\W)).{8,20}$/;
    if (!pwdRegExp.test(currentPwd)) {
      setPasswordMessage("Please enter a valid Password.");
      setIsPassword(false);
    } else {
      setPasswordMessage("");
      setIsPassword(true);
    }
  };

  const navigate = useNavigate();
  // 기존 아이디/ 이메일이 중복될 경우?
  const onSubmitHandler = async (event) => {
    event.preventDefault();
    await axios
      .post(`${process.env.REACT_APP_API_URL}/api/members`, {
        name,
        email,
        password,
      },
        // { withCredentials: true }
      )
      .then((response) => {
        console.log(response);
        navigate("/login");
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const passwordVaild = `Passwords must contain at least eight characters, including at least 1 special letter and 1 number.`;
  const signupCheckbox = `Opt-in to receive occasional product updates, user research invitations, company announcements, and digests.`;
  return (
    <div>
      <Container>
        <LeftSideContainer>
          <h1>Join the Stack Overflow community</h1>
          <div>
            <br />
            <div>
              <img
                className="signupImage"
                width="24"
                alt="logo1"
                src="../assets/quesiton.png"
              />{" "}
              Get unstuck — ask a question
            </div>
            <br />
            <div>
              <img
                className="signupImage"
                width="24"
                alt="logo2"
                src="../assets/arrow.png"
              />{" "}
              Unlock new privileges like voting and commenting
            </div>
            <br />
            <div>
              <img
                className="signupImage"
                width="24"
                alt="logo3"
                src="../assets/bookmark.png"
              />{" "}
              Save your favorite tags, filters, and jobs
            </div>
            <br />
            <div>
              <img
                className="signupImage"
                width="24"
                alt="logo4"
                src="../assets/trophy.png"
              />{" "}
              Earn reputation and badges
            </div>
            <br />
            <div className="greyfont">
              Collaborate and share knowledge with a private group for FREE.
            </div>
            <div>
              <Link to="https://stackoverflow.co/teams/?utm_source=so-owned&utm_medium=product&utm_campaign=free-50&utm_content=public-sign-up" className="signuplink">
                <div>Get Stack Overflow for Teams free for up to 50 users.</div>
              </Link>
            </div>
          </div>
        </LeftSideContainer>
        <SignupContainer>
          {/*소셜 로그인 폼*/}
          <section className="social-signup-container">
            <button className="google">
              <Google />
              <span>Sign up with Google</span>
            </button>
            <button className="github">
              <Github />
              <span>Sign up with Github</span>
            </button>
            <button className="facebook">
              <Facebook />
              <span>Sign up with Facebook</span>
            </button>
          </section>
          {/*이메일 비밀번호 입력창*/}
          <section className="account-signup-container">
            <form onSubmit={onSubmitHandler}>
              <div className="name">
                <label htmlFor="name" className="name-label">
                  Display name
                </label>
                <div className="name-input">
                  <input
                    id="name"
                    value={name}
                    onChange={onNameHandler}
                  ></input>
                </div>
              </div>
              <div className="email">
                <label htmlFor="email" className="email-label">
                  Email
                </label>
                <div className="email-input">
                  <input
                    id="email"
                    value={email}
                    onChange={onEmailHandler}
                  ></input>
                  <p className="message">{emailMessage}</p>
                </div>
              </div>
              <div className="password">
                <label htmlFor="password" className="password-label">
                  Password
                </label>
                <div className="password-input">
                  <input
                    id="password"
                    type="password"
                    value={password}
                    onChange={onPasswordHandler}
                  ></input>
                  <p className="message">{passwordMessage}</p>
                </div>
                <div className="password-valid">{passwordVaild}</div>
              </div>
              <div className="checkbox-wrap">
                <input type="checkbox" />
                <label>{signupCheckbox}</label>
              </div>
              <div className="Signup">
                <button className="signup-btn" type="submit">
                  Sign up
                </button>
              </div>
            </form>
            <Policy>
              By clicking “Sign up”, you agree to our
              <Link to="https://stackoverflow.com/legal/terms-of-service/public" className="signuplink">
                <div>terms of service,</div>
              </Link>
              <Link to="https://stackoverflow.com/legal/privacy-policy" className="signuplink">
                <div>privacy policy</div>
              </Link>{" "}
              and{" "}
              <Link to="https://stackoverflow.com/legal/cookie-policy" className="signuplink">
                <div>cookie policy</div>
              </Link>
            </Policy>
          </section>
        </SignupContainer>
      </Container>
    </div>
  );
};

const Container = styled.section`
  display: flex;
  background-color: rgb(237, 239, 240);
  justify-content: center;
`;

const LeftSideContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 250px 30px 20px 100px;
  .greyfont {
    color: grey;
  }
`;

const SignupContainer = styled.section`
  width: 400px;
  display: flex;
  flex-direction: column;
  margin: 50px 40px 20px 100px;

  .social-signup-container {
    width: 400px;
    margin-bottom: 20px;
    display: flex;
    flex-direction: column;
  }
  .social-signup-container > button {
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

  .message {
    color: red;
  }

  .account-signup-container {
    width: 400px;
    height: 600px;
    margin-bottom: 30px;
    padding: 30px 30px;
    border-radius: 10px;
    box-shadow: 0px 0px 10px #a7a7a7;
    background-color: #ffffff;
    > div {
      margin-bottom: 25px;
    }
    & input {
      width: 340px;
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
      width: 340px;
      height: 50px;
      margin: 10px 0;
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
  & a {
    font-weight: 500;
    color: #0b79ce;
    :hover {
      color: #4da4de;
      cursor: pointer;
    }
  }
`;
const Policy = styled.div`
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
`;

export default Signup;
