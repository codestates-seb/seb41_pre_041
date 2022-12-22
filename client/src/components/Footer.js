import styled from "styled-components";

const FooterWrap = styled.div`
    display: flex;
    justify-content: center;
    width: 100%;

    font-size: 13px;
    background: #232629;
`

const FooterContainer = styled.div`
    display: flex;
    width: 1264px;
    min-width: 1264px;

    padding: 32px 12px 12px 12px;
    .logo { flex: 0 0 64px; }
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
        flex: 1 1 150px;
        .list {
            display: flex;
            li:first-child{ margin-left: 0px; }
            li { margin-left: 12px; }
            a {
                padding: 4px 0px;
                font-size: 11px;
            }
        }
    }
`

function Footer() {
    return(
        <FooterWrap>
            <FooterContainer>
                <div className="logo">
                    <a href="/">
                        <svg width="32" height="37" viewBox="0 0 32 37">
                            <path d="M26 33v-9h4v13H0V24h4v9h22Z" fill="#BCBBBB"></path>
                            <path d="m21.5 0-2.7 2 9.9 13.3 2.7-2L21.5 0ZM26 18.4 13.3 7.8l2.1-2.5 12.7 10.6-2.1 2.5ZM9.1 15.2l15 7 1.4-3-15-7-1.4 3Zm14 10.79.68-2.95-16.1-3.35L7 23l16.1 2.99ZM23 30H7v-3h16v3Z" fill="#F48024"></path>
                        </svg>
                    </a>
                </div>
                <nav className="nav">
                    <div className="footer-list">
                        <h5 className="title">
                            <a href="/">STACK OVERFLOW</a>
                        </h5>
                        <ul>
                            <li><a className="link" href="/">Questions</a></li>
                            <li><a className="link" href="/">Help</a></li>
                        </ul>
                    </div>
                    <div className="footer-list">
                        <h5 className="title">
                            <a href="/">PRODUCTS</a>
                        </h5>
                        <ul>
                            <li><a className="link" href="/">Teams</a></li>
                            <li><a className="link" href="/">Advertising</a></li>
                            <li><a className="link" href="/">Collectives</a></li>
                            <li><a className="link" href="/">Talent</a></li>
                        </ul>
                    </div>
                    <div className="footer-list">
                        <h5 className="title">
                            <a href="/">COMPANY</a>
                        </h5>
                        <ul>
                            <li><a className="link" href="/">About</a></li>
                            <li><a className="link" href="/">Press</a></li>
                            <li><a className="link" href="/">Work Here</a></li>
                            <li><a className="link" href="/">Legal</a></li>
                            <li><a className="link" href="/">Privacy Policy</a></li>
                            <li><a className="link" href="/">Terms of Service</a></li>
                            <li><a className="link" href="/">Contact Us</a></li>
                            <li><a className="link" href="/">Cookie Settings</a></li>
                            <li><a className="link" href="/">Cookie Policy</a></li>
                        </ul>
                    </div>
                    <div className="footer-list">
                        <h5 className="title">
                            <a href="/">STACK EXCHANGE NETWORK</a>
                        </h5>
                        <ul>
                            <li><a className="link" href="/">Technology</a></li>
                            <li><a className="link" href="/">Culture & recreation</a></li>
                            <li><a className="link" href="/">Life & arts</a></li>
                            <li><a className="link" href="/">Science</a></li>
                            <li><a className="link" href="/">Professional</a></li>
                            <li><a className="link" href="/">Business</a></li>
                            <li className="mg25"><a className="link" href="/">API</a></li>
                            <li><a className="link" href="/">Data</a></li>
                        </ul>
                    </div>
                </nav>
                <div className="copyrigth">
                    <ul className="list">
                        <li><a className="link" href="/">Blog</a></li>
                        <li><a className="link" href="/">Fackbook</a></li>
                        <li><a className="link" href="/">Twitter</a></li>
                        <li><a className="link" href="/">LinkdIn</a></li>
                        <li><a className="link" href="/">Instagram</a></li>
                    </ul>
                </div>
            </ FooterContainer>
        </ FooterWrap>
    )
}

export default Footer;