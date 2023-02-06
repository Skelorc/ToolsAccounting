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
}

async function postTestRequest(){
  let response = await postData("/reports/test-post", {})
  console.log("🚀 ~ file: TestComponent.jsx:31 ~ postTestRequest ~ repsponse", response)
}



function TestComponent() {
  return (
    <div className="testComponent" data-aos="slide-down" data-aos-delay="200">
      <div className="container">
        <div className="testComponent__data">
          Ответы с тестовых запросов пока в консоли, на f12
        </div>
      </div>
    </div>
  )
}

export default TestComponent;