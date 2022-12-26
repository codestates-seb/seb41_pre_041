import React, { useState } from "react";
// import { useDispatch } from 'react-redux';
import styled from 'styled-components';
import { ReactComponent as Google } from "../assets/google-icon.svg";
import { ReactComponent as Github } from "../assets/github-icon.svg";
import { ReactComponent as Facebook } from "../assets/facebook-icon.svg";

const Signup = (props) => {
  const [name, setName] = useState("")
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("")

  const onNameHandler = (event) => {
    setName(event.currentTarget.value)
  }
  const onEmailHandler = (event) => {
    setEmail(event.currentTarget.value)
  }

  const onPasswordHandler = (event) => {
    setPassword(event.currentTarget.value)
  }

  const onSubmitHandler = (event) => {
    event.preventDefault();
  }

  //   if (password !== /^(?=.*[a-zA-Z])((?=.*\d)(?=.*\W)).{8,16}$/) {
  //     return alert('password is not valid')
  //   }

  //   let body = {
  //     name: name,
  //     email: email,
  //     password: password,
  //   }

  //   dispatch(registerUser(body))
  //     .then(response => {
  //       if (response.payload.success) {
  //         props.history.push('/loginPage')
  //       } else {
  //         alert('Error')
  //       }
  //     })
  // }

  const passwordVaild = `Passwords must contain at least eight characters, including at least 1 special letter and 1 number.`

  return (
    <div>
      < SignupContainer >
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
        < section className="account-signup-container">
          <form onSubmit={onSubmitHandler}>
            <div className="name">
              <label htmlFor="name" className="name-label">
                Display name
              </label>
              <div className="name-input">
                <input id="name" value={name} onChange={onNameHandler}></input>
              </div>
            </div>
            <div className="email">
              <label htmlFor="email" className="email-label">
                Email
              </label>
              <div className="email-input">
                <input id="email" value={email} onChange={onEmailHandler}></input>
              </div>
            </div>
            <div className="password">
              <label htmlFor="password" className="password-label">
                Password
              </label>
              <div className="password-input">
                <input id="password" value={password} onChange={onPasswordHandler}></input>
              </div>
              <div className="password-valid">{passwordVaild}</div>
            </div>
            <div className="Signup">
              <button className="signup-btn">Sign up</button>
            </div>
          </form>
        </section >
      </SignupContainer >
    </div >
  )
};

const SignupContainer = styled.section`
      width: 400px;
      display: flex;
      flex-direction: column;
      justify-content: center;
      float: right;
      margin: 50px 0;
      margin-right: 50px;

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
        margin - left: 6px;
    }
  }

      .google {
        color: #2f3337;
      background-color: #ffffff;
      border: 1px solid #a7a7a7;
      &:hover {
        background - color: #f5f5f5;
      color: #2f3337;
    }
  }
      .github {
        color: #ffffff;
      background-color: #2f3337;
      border-color: transparent;
      &:hover {
        background - color: #000000;
      color: #ffffff;
    }
  }
      .facebook {
        color: #ffffff;
      background-color: #385499;
      border-color: transparent;
      &:hover {
        background - color: #364984;
      color: #ffffff;
    }
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
        margin - bottom: 25px;
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
        font - weight: 500;
      color: #0b79ce;
      :hover {
        color: #4da4de;
      cursor: pointer;
      }
    }
  }
      `;

export default Signup;
