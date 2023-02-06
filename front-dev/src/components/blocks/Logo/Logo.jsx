import { NavLink } from "solid-app-router";
import "./logo.sass"

function Logo({img = "vertical_on_transparent_by_logaster.png", parent=false}) {
  return (
    <NavLink className={`logo ${parent ? `${parent}__logo` : ""}`} href="/">
      <img src={`/images/logo/${img}`} className="logo__img"  alt="" />
    </NavLink>
  )
}

export default Logo;