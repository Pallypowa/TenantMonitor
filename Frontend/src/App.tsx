import Navbar from "./components/Navbar"
import "./style.css"
import { Route, Routes } from "react-router-dom"
import Home from "./pages/Home"
import Pricing from "./pages/Pricing"
import About from "./pages/About"

function App() {
    return (
        <>
            <Navbar />
            <div className="container">
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/pricing" element={<Pricing />} />
                    <Route path="/about" element={<About />} />
                </Routes>
            </div>
        </>
    )
}

export default App
