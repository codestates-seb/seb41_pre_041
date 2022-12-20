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
    font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
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
