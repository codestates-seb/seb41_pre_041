import styled from "styled-components";
import { Link } from "react-router-dom";

const FooterWrap = styled.div`
  display: flex;
  justify-content: center;
  width: 100%;
  font-size: 13px;
  background: #232629;
`;

const FooterContainer = styled.div`
  display: flex;
  width: 1264px;
  min-width: 1264px;
  padding: 32px 12px 12px 12px;
  .logo {
    flex: 0 0 64px;
  }
  .nav {
    display: flex;
    flex: 2 1 auto;
    flex-wrap: wrap;
  }
  .title {
    margin-bottom: 12px;
    a {
      font-weight: bold;
      color: #babfc4;
    }
  }
  .link {
    display: block;
    color: #9199a1;
    padding: 4px 0px;
  }
  .link:hover {
    color: #9fa6ad;
  }

  .footer-list {
    flex: 1 0 auto;
    padding: 0px 12px 24px 0px;
  }

  .mg25 {
    margin-top: 25px;
  }

  .copyrigth {
    display: flex;
    flex-direction: column;
    justify-content: space-between;

    flex: 1 1 150px;
    .list {
      display: flex;
      li:first-child {
        margin-left: 0px;
      }
      li {
        margin-left: 12px;
      }
      a {
        padding: 4px 0px;
        font-size: 11px;
      }
    }
    p {
      margin-bottom: 24px;
      font-size: 11px;
      color: #9199a1;
      a {
        color: #9199a1;
        text-decoration: underline;
      }
    }
  }
`;

function Footer() {
  return (
    <FooterWrap>
      <FooterContainer>
        <div className="logo">
          <Link to="/">
            <svg width="32" height="37" viewBox="0 0 32 37">
              <path d="M26 33v-9h4v13H0V24h4v9h22Z" fill="#BCBBBB"></path>
              <path
                d="m21.5 0-2.7 2 9.9 13.3 2.7-2L21.5 0ZM26 18.4 13.3 7.8l2.1-2.5 12.7 10.6-2.1 2.5ZM9.1 15.2l15 7 1.4-3-15-7-1.4 3Zm14 10.79.68-2.95-16.1-3.35L7 23l16.1 2.99ZM23 30H7v-3h16v3Z"
                fill="#F48024"
              ></path>
            </svg>
          </Link>
        </div>
        <nav className="nav">
          <div className="footer-list">
            <h5 className="title">
              <Link to="/">STACK OVERFLOW</Link>
            </h5>
            <ul>
              <li>
                <Link className="link" to="/notfound">
                  Questions
                </Link>
              </li>
              <li>
                <Link className="link" to="/notfound">
                  Help
                </Link>
              </li>
            </ul>
          </div>
          <div className="footer-list">
            <h5 className="title">
              <Link to="/questions">PRODUCTS</Link>
            </h5>
            <ul>
              <li>
                <Link className="link" to="/notfound">
                  Teams
                </Link>
              </li>
              <li>
                <Link className="link" to="/notfound">
                  Advertising
                </Link>
              </li>
              <li>
                <Link className="link" to="/notfound">
                  Collectives
                </Link>
              </li>
              <li>
                <Link className="link" to="/notfound">
                  Talent
                </Link>
              </li>
            </ul>
          </div>
          <div className="footer-list">
            <h5 className="title">
              <Link to="/notfound">COMPANY</Link>
            </h5>
            <ul>
              <li>
                <Link className="link" to="/notfound">
                  About
                </Link>
              </li>
              <li>
                <Link className="link" to="/notfound">
                  Press
                </Link>
              </li>
              <li>
                <Link className="link" to="/notfound">
                  Work Here
                </Link>
              </li>
              <li>
                <Link className="link" to="/notfound">
                  Legal
                </Link>
              </li>
              <li>
                <Link className="link" to="/notfound">
                  Privacy Policy
                </Link>
              </li>
              <li>
                <Link className="link" to="/notfound">
                  Terms of Service
                </Link>
              </li>
              <li>
                <Link className="link" to="/notfound">
                  Contact Us
                </Link>
              </li>
              <li>
                <Link className="link" to="/notfound">
                  Cookie Settings
                </Link>
              </li>
              <li>
                <Link className="link" to="/notfound">
                  Cookie Policy
                </Link>
              </li>
            </ul>
          </div>
          <div className="footer-list">
            <h5 className="title">
              <Link to="/notfound">STACK EXCHANGE NETWORK</Link>
            </h5>
            <ul>
              <li>
                <Link className="link" to="/notfound">
                  Technology
                </Link>
              </li>
              <li>
                <Link className="link" to="/notfound">
                  Culture & recreation
                </Link>
              </li>
              <li>
                <Link className="link" to="/notfound">
                  Life & arts
                </Link>
              </li>
              <li>
                <Link className="link" to="/notfound">
                  Science
                </Link>
              </li>
              <li>
                <Link className="link" to="/notfound">
                  Professional
                </Link>
              </li>
              <li>
                <Link className="link" to="/notfound">
                  Business
                </Link>
              </li>
              <li className="mg25">
                <Link className="link" to="/notfound">
                  API
                </Link>
              </li>
              <li>
                <Link className="link" to="/notfound">
                  Data
                </Link>
              </li>
            </ul>
          </div>
        </nav>
        <div className="copyrigth">
          <ul className="list">
            <li>
              <Link className="link" to="/notfound">
                Blog
              </Link>
            </li>
            <li>
              <Link className="link" to="/notfound">
                Fackbook
              </Link>
            </li>
            <li>
              <Link className="link" to="/notfound">
                Twitter
              </Link>
            </li>
            <li>
              <Link className="link" to="/notfound">
                LinkdIn
              </Link>
            </li>
            <li>
              <Link className="link" to="/notfound">
                Instagram
              </Link>
            </li>
          </ul>
          <p>
            Site design / logo © 2022 Stack Exchange Inc; user contributions
            licensed under <Link to="notfound">CC BY-SA.</Link> rev
            2022.12.21.43127
          </p>
        </div>
      </FooterContainer>
    </FooterWrap>
  );
}

export default Footer;
