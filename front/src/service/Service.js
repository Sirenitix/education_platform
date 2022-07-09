import { useNavigate } from "react-router-dom";
export class Service {
  navigate = useNavigate();

  async handleLogin(userData) {
    console.log("hey");
    console.log(JSON.stringify(userData));
    const res = await fetch("http://164.92.192.48:8081/authenticate", {
      method: "POST",
      body: JSON.stringify(userData), //TODO: check this point, maybe they need body
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
    });
    const resJson = await res.json();
    console.log(resJson);
    if (res.status === 200) {
      sessionStorage.setItem("access_token", resJson.token);
      console.log(sessionStorage.getItem("access_token"));
      this.navigate("/profile");
    }
  }

  async logout() {
    sessionStorage.removeItem("access_token");
    this.navigate("/");
  }
}
