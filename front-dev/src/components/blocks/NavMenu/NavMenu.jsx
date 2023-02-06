import "./navMenu.sass"
import { NavLink } from "solid-app-router";

function switchVisibility(e){
  e.target.closest(".navMenu").classList.toggle("navMenu_visible")
}

function NavMenu({parent, isHamburgered=false}) {
  return (
    <nav className={`navMenu ${parent}__navMenu ${isHamburgered ? "navMenu_hamburgered" : ""}`}>
      <div className="navMenu__hamburger" onclick={switchVisibility}>
        <svg version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
          viewBox="0 0 352 320" style="enable-background:new 0 0 352 320;" xml:space="preserve" className="navMenu__hamburgerIcon">
          <rect width="352" height="32"/>
          <rect y="144" width="352" height="32"/>
          <rect y="288" width="352" height="32"/>
        </svg>
      </div>
      <ul className="navMenu__list">
        <li className="navMenu__item">
          <NavLink href="/main"         className="navMenu__link" activeClass="navMenu__link_active">
            Главная
          </NavLink>
        </li>
        <li className="navMenu__item">
          <NavLink href="/services" className="navMenu__link" activeClass="navMenu__link_active">
            Услуги
          </NavLink>
        </li>
        <li className="navMenu__item">
          <NavLink href="/projects" className="navMenu__link" activeClass="navMenu__link_active">
            Проекты
          </NavLink>
        </li>
        <li className="navMenu__item">
          <NavLink href="/contacts" className="navMenu__link" activeClass="navMenu__link_active">
            Контакты
          </NavLink>
        </li>
        <li className="navMenu__item navMenu__item_socials">
          <a href="#" className="navMenu__socisalsLink">
            <img src="/images/socials/whatsapp.svg" className="navMenu__socialsIcon" alt="" />
          </a>
          <a href="#" className="navMenu__socisalsLink">
            <img src="/images/socials/telegram.svg" className="navMenu__socialsIcon" alt="" />
          </a>
          <a href="#" className="navMenu__socisalsLink">
            <img src="/images/socials/phone.svg" className="navMenu__socialsIcon" alt="" />
          </a>
        </li>
      </ul>
    </nav>
  )
}

export default NavMenu;