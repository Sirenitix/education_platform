import { useFormik } from "formik";
import "./Login.css";
// import { useState } from "react";
import { Flex, Box, Text } from "@chakra-ui/react";
import { Service } from "../../service/Service";

const Login = () => {
  const service = new Service();
  const validate = (values) => {
    const errors = {};
    const passRegex = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$/g;

    if (!passRegex.test(values.password)) {
      errors.password = "";
    }

    return errors;
  };
  const formik = useFormik({
    initialValues: {
      username: "",
      password: "",
    },
    validate,
    onSubmit: (values) => {
      // alert(JSON.stringify(values, null, 2));
      service.handleLogin(values);
    },
  });

  return (
    <>
      <Flex color="black">
        <Box
          flex="1.3"
          height={"100vh"}
          textAlign={"left"}
          display={"flex"}
          flexDirection={"column"}
          justifyContent={"center"}
          alignItems={"left"}
        >
          <Text fontSize="6xl" color={"#12492F"} fontWeight={"bold"}>
            SHARE
          </Text>
          <Text fontSize="3xl" color={"black"} fontWeight={"bold"}>
            School Hub for Action Research<br></br> in Education
          </Text>
        </Box>
        <Box
          flex="0.8"
          height={"100vh"}
          display={"flex"}
          flexDirection={"column"}
          justifyContent={"center"}
          alignItems={"left"}
        >
          <>
            <div className="min-h-full flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
              <div className="max-w-md w-full space-y-8">
                <div>
                  <Text fontSize="xl" color={"black"} fontWeight={"bold"}>
                    Авторизация
                  </Text>
                </div>
                <form className="mt-8 space-y-6" onSubmit={formik.handleSubmit}>
                  <input type="hidden" name="remember" defaultValue="true" />
                  <div className="rounded-md shadow-sm -space-y-px">
                    <div>
                      <label htmlFor="email-address" className="sr-only">
                        Email address
                      </label>
                      <input
                        id="email-address"
                        name="username"
                        type="email"
                        autoComplete="email"
                        required
                        className="appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-t-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                        placeholder="Email address"
                        value={formik.values.username}
                        onChange={formik.handleChange}
                      />
                    </div>
                    <div>
                      <label htmlFor="password" className="sr-only">
                        Password
                      </label>
                      <input
                        id="password"
                        name="password"
                        type="password"
                        autoComplete="current-password"
                        required
                        className="appearance-none rounded-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-b-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
                        placeholder="Password"
                        value={formik.values.password}
                        onChange={formik.handleChange}
                      />
                      {formik.errors.password && (
                        <span>{formik.errors.password}</span>
                      )}
                    </div>
                  </div>
                  <div style={{ display: "flex", justifyContent: "center" }}>
                    <button
                      type="submit"
                      className="group relative w-full flex justify-center py-4 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                      style={{
                        backgroundColor: "#F7A325",
                        width: "154px",
                        height: "50px",
                        display: "flex",
                        justifyContent: "center",
                      }}
                    >
                      Войти
                    </button>
                  </div>
                </form>
              </div>
            </div>
          </>
        </Box>
      </Flex>
    </>
  );
};
export default Login;
