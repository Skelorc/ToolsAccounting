import Logo from "../../blocks/Logo/Logo";
import NavMenu from "../../blocks/NavMenu/NavMenu";
import "../../utility/layout/container/container.sass";
import "./header.sass"

function Header() {
  return (
    <header className="header" data-aos="slide-down" data-aos-delay="200">
      <div className="container">
        <div className="header__wrapper">
          <Logo img={"vertical_on_trans_white.svg"} parent={"header"}/>
          <NavMenu parent={"header"} isHamburgered={true}/>
        </div>
      </div>
    </header>
  )
}

export default Header;