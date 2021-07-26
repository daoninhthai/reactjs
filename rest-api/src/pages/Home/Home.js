import "./Home.css";

import Footer from "../../App/Footer/Footer";
import NewPost from "../../App/NewestPost/NewPost";
import SideBar from "../../App/Sidebar/SideBar";
import { useLocation } from "react-router";

export default function Home() {
  const location = useLocation();
  console.log(location);
  return (
    <>
      
      <div className="home container">
        <NewPost />
        <SideBar />
        
      </div>
     
    </>
  );
}
