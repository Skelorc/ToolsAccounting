import "./testComponent.sass"
import { postData } from "../../../App";


// Method : GET
// /reports/test-get


// return data:
// {
// "some_key" : (Пустой юзер),
// "admin" : (Юзер с данными)
// }

// Method: POST
// /reports/test-post


// return data:
// {
// "content" : (список категорий);
// }

async function getTestRequest(){
  let response = await fetch("/reports/test-get")
  console.log("🚀 ~ file: TestComponent.jsx:26 ~ getTestRequest ~ response", response)
  console.log("POST response body:", response.json())
}

async function postTestRequest(){
  let response = await postData("/reports/test-post", {})
  console.log("🚀 ~ file: TestComponent.jsx:31 ~ postTestRequest ~ repsponse", response)
  console.log("POST response body:", response)

}



function TestComponent() {
  return (
    <div className="testComponent section" data-aos="slide-down" data-aos-delay="200">
      <div className="container">
        <div className="testComponent__title section__title">
          тестовый компонент
        </div>
        <div className="testComponent__textfield textfield">
          <p>
            Ответы пока в консоли на f12
          </p>
        </div>
        <div className="testComponent__controls">
          <button className="btn" onclick={getTestRequest}>
            Отправить GET запрос
          </button>
          <button className="btn" onclick={postTestRequest}>
            Отправить POST запрос
          </button>
        </div>
      </div>
    </div>
  )
}

export default TestComponent;