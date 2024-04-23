import { useState } from "react";
import { useForm } from "react-hook-form";

import { FormContainer } from "src/components";

import { loginUser } from "src/services";
import { loginFormFields } from "src/components/forms/fields";

import "./style.scss";

const LoginForm = () => {
  const [error, setError] = useState(null);

  const methods = useForm({
    mode: "onBlur" // validate on blur, when user moves to the next field
  });

  const onSubmit = (data) => {
    loginUser(data)
      .then(() => {
        // home page
        window.location.href = "/";
      }).catch((error) => {
        setError(error.message);
      });
  }

  return (
    <div className="login-form-container">
        <h5 className="form-title">LOGIN</h5>
        <div className="login-form">
          <FormContainer 
            formFields={ loginFormFields } 
            onSubmit={ methods.handleSubmit(onSubmit) } 
            buttonLabel="LOGIN" 
            methods={ methods }
          />
        <span className="forgot-password body-bold">Forgot password?</span>
        </div>
    </div>
  )
}

export default LoginForm
