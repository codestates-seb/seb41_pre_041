import React from 'react';
import { createGlobalStyle } from 'styled-components';
import StackHeader from './components/StackHeader';

// CSS 초기화
const GlobalStyle = createGlobalStyle`
  *{
    margin: 0;
    padding: 0;
    box-sizing: border-box;
    list-style: none;
    text-decoration: none;
  }
`

function App() {
  return (
    <>
    <GlobalStyle />
      <StackHeader />
    </>
  );
}

export default App;
