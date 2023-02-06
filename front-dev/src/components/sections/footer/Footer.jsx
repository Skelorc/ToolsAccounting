import Logo from "../../blocks/Logo/Logo";
import NavMenu from "../../blocks/NavMenu/NavMenu";
import "./footer.sass"

function Footer() {
  return (
    <footer className="footer">
      <div className="container">
        <div className="footer__wrapper">
          <Logo img={"horizontal_corp.png"} parent={"footer"}/>
          <NavMenu parent={"footer"}/>
        </div>
        <div className="footer__wrapper">
          <div className="footer__copyright">
            Copyright © 2019 - 2023
          </div>
        </div>
      </div>
    </footer>
  )
}

export default Footer;