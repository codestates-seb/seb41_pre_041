import React, { useState } from "react";
import styled from 'styled-components';
import GoogleAuth from '../components/GoogleAuth';

const Signup = () => {
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

  const passwordVaild = `Passwords must contain at least eight characters, including at least 1 special letter and 1 number.`


  return (
    <div>
      <SocialSignup>
        <GoogleAuth />
      </SocialSignup>
      <div class="signup">
        <form>
          <label>Display name</label>
          <div><input type="text" value={name} onChange={onNameHandler} /></div>
          <label>Email</label>
          <div><input type="email" value={email} onChange={onEmailHandler} /></div>
          <label>Password</label>
          <div><input type="password" value={password} onChange={onPasswordHandler} /></div>
          <div>{passwordVaild}</div>
          <div><button type="submit">Sign up</button></div>
        </form>
      </div>
    </div>
  )
};

const SocialSignup = styled.div`
display: flex;
margin-top: 50px;
`;

export default Signup;
