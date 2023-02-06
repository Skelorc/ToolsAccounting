/* @refresh reload */
import { render } from 'solid-js/web';
import { Router } from 'solid-app-router';
import './index.sass';
import App from './App';
import AOS from 'aos';
import 'aos/dist/aos.css'
import { createSignal } from 'solid-js';
AOS.init(
  {
  once: false,
  // delay: 150,
  duration: 600,
  // offset: 50
  mirror: true,
  easing: "ease-out",
  anchorPlacement: "bottom-bottom"

}
)
const baseAddress = ""
let localhostAdr  = "http://localhost:5000"
let deployAdr     = ""


let [currUrl, setCurrUrl] = createSignal("")
setCurrUrl(localhostAdr)
console.log("currUrl", currUrl())
render(() => 
    <Router>
      <App />
    </Router>
  , document.getElementById('root'));

export {currUrl, setCurrUrl}