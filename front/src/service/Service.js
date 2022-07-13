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

    // this.navigate("/profile");
  }

  async logout() {
    sessionStorage.removeItem("access_token");
    this.navigate("/");
  }

  async addPost(postContent) {
    const token = sessionStorage.getItem("access_token");
    console.log(token);
    console.log(JSON.stringify(postContent));
    const res = await fetch(
      "http://164.92.192.48:8081/reflection/create-post",
      {
        method: "POST",
        body: JSON.stringify(postContent), //TODO: check this point, maybe they need body
        headers: {
          "Content-type": "application/json",
          Authorization: ` ${token}`,
        },
      }
    );
    const postRes = await res;
    console.log(postRes);
  }

  async getUserPosts() {
    // const page = 1;
    const res = await fetch(
      "http://164.92.192.48:8081/reflection/posts?page=0",
      {
        method: "GET",
        // headers: {
        //   Accept: "application/json",
        //   "Content-Type": "application/json",
        // },
      }
    );
    const getPostRes = await res.json();
    console.log(getPostRes);
  }

  async sendComment(comment, postId) {
    const token = sessionStorage.getItem("access_token");
    console.log(token);
    console.log(JSON.stringify(comment));
    const res = await fetch(
      `http://164.92.192.48:8081/reflection-comment?content=${comment}&name=name&postId=${postId}`,
      {
        method: "POST",
        headers: {
          "Content-type": "application/json",
          Authorization: ` ${token}`,
        },
      }
    );
    const resJson = await res.json();
    console.log(resJson);

    // this.navigate("/profile");
  }

  async createProject(projectData) {
    console.log("hey");
    const token = sessionStorage.getItem("access_token");

    console.log(JSON.stringify(projectData));
    const project_name = projectData.project_name;
    const userArr = projectData.users;
    const res = await fetch(
      `http://164.92.192.48:8081/createProject?name=${"gggggg"}&project_name=${project_name}&users=${userArr}
    `,
      {
        method: "POST",
        headers: {
          "Content-type": "application/json",
          Authorization: ` ${token}`,
        },
      }
    );
    // const resJson = await res.json();
    console.log(res);

    // this.navigate("/profile");
  }
}
