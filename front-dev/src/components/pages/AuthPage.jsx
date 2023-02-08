import "../utility/legacyCss/authorization.css"
import { postData, postForm } from "../../App";


async function authBtnClick(e){
  let formToSubmit = document.querySelector(".login_right__panel_form")
  let formData = new FormData(formToSubmit)
  let response = await postForm("login", formData)
  console.log("🚀 ~ file: AuthPage.jsx:9 ~ authBtnClick ~ response", response)
}

function AuthPage() {
  return (
    <div class="wrapper d-flex">
        <a href="/" class="login_left__panel d-flex">
            {/* <img th:src="@{/img/logo/panel-logo2x.webp}" class="login_left__panel_img"></img> */}
            <img src="" class="login_left__panel_img"></img>
            <div class="login_left__panel_text">MAD DOG</div>
        </a>
        <div>
            {/* <span th:if="${param.loginError!=null}"
                  th:text="'Вы ввели неправильный логин/пароль'">
            </span> */}
            <span>
              Сообщение о неправильном пароле
            </span>
        </div>
        <div class="login_right__panel d-flex">
            {/* <form class="login_right__panel_form d-flex fd_col" th:action="@{/login}" method="post"> */}
            <form class="login_right__panel_form d-flex fd_col"  action="http://192.168.0.12:8080/login" method="post">
                <div class="panel_form__text d-flex">
                    ВХОД В СИСТЕМУ
                </div>
                <div class="panel_form__input d-flex fd_col">
                    <div class="panel_form__input_login d-flex fd_col">
                        <label class="input_login__text d-flex">Логин*</label>
                        <input class="input_login__input d-flex" type="text" name="username"
                              placeholder="Можете ввести свой логин, телефон или почту..." autocomplete="on" required></input>
                    </div>
                    <div class="panel_form__input_password d-flex fd_col"></div>
                    <label class="input_password__text">Пароль*</label>
                    <input class="input_password__input" type="password" name="password"
                          placeholder="Введите свой пароль..." autocomplete="on" required></input>
                </div>
                <div class="panel_form__input_button d-flex">
                    <button class="input_button__button" value="ВХОД"  onclick={authBtnClick}> Отправить </button>
                </div>
            </form>
        </div>
    </div>
  )
}

export default AuthPage;