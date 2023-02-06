import './app.sass'
import Header from './components/sections/Header/Header';
import { Navigate, Route, Routes } from "solid-app-router";
import MainPage from './components/pages/MainPage';
import { createSignal } from 'solid-js';
import Footer from './components/sections/footer/Footer';
import { createEffect, For } from "solid-js"



async function postData(url = '', data = {}) {
  // Default options are marked with *
  const response = await fetch(`${url}`, {
    method: 'POST', // *GET, POST, PUT, DELETE, etc.
    mode: 'cors', // no-cors, *cors, same-origin
    cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
    credentials: 'include', // include, *same-origin, omit
    headers: {
      Accept: 'application/json',
      'Content-Type': 'application/json'
      // 'Content-Type': 'application/x-www-form-urlencoded',
    },
    redirect: 'follow', // manual, *follow, error
    referrerPolicy: 'no-referrer', // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
    body: JSON.stringify(data) // body data type must match "Content-Type" header
  })
  return response.json(); // parses JSON response into native JavaScript objects
}

async function getTextField(sectionName, signalSetter) {
  createEffect(async()=>{
    const response = await postData(`/textfield/read`, {sectionName: sectionName})
    response.textfield
      ?
        signalSetter(response.textfield)
      :
        signalSetter("")
  })
}

const [ourProjectsPreviews, setOurProjectsPreviews] = createSignal([])
const [clientsList, setClientsList] = createSignal([])
const [servicesList, setServicesList] = createSignal([])
const [singleService, setSingleService] = createSignal({
  "name": "",
  "preview": "",
  "description": JSON.stringify(""),
  "textfield": JSON.stringify(""),
  "link": ""
})


function App() {
  return (
    <div class="app">
      <div class="app__window" aos-data="fade-in" data-aos-duration="900">
        <Header />
        <main className="main">
          <Routes>
            <Route path="/" element={<Navigate href="/main"/>}/>
            <Route path="/main" element={<MainPage />} />
          </Routes>
        </main>
        <Footer />
      </div>
    </div>
  )
}

export {ourProjectsPreviews, setOurProjectsPreviews, postData, clientsList, setClientsList, servicesList, setServicesList, singleService, setSingleService, getTextField}
export default App;