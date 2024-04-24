import { useState } from "react";
import { Link } from "react-router-dom";
import { useForm } from "react-hook-form";

import { FormContainer } from "src/components";

import { registerUser  } from "src/services";
import { ROUTE_PATHS, BUTTON_LABELS } from "src/constants";
import { registerFormFields } from "src/forms/fields";

import "./style.scss";

const RegisterForm = () => {
  const [error, setError] = useState(null);

  const methods = useForm({
    mode: "onBlur"
  });

  const onSubmit = (data) => {
    data = { ...data, role: "USER" };

    registerUser(data)
      .then(() => {
        setError(null);
        // redirect to login page
        window.location.href = ROUTE_PATHS.LOGIN;
      })
      .catch((error) => {
        setError(error.response.data.message);
    });
  }

  return (
    <div className="register-form-container">
      <h5 className="form-title">REGISTER</h5>
      <div className="register-form">
        { error && 
          <span className="error-message body-bold">
            { error }
          </span> 
        }
        <FormContainer 
          formFields={ registerFormFields } 
          onSubmit={ methods.handleSubmit(onSubmit) }
          buttonLabel={ BUTTON_LABELS.REGISTER }
          methods={ methods }
        />
        <div className="form-link body-bold">
          <span>Already have an account? </span>
          <Link to={ ROUTE_PATHS.LOGIN }>
            <span className="login">Login</span>
          </Link>
        </div>
      </div>
    </div>
  )
}

export default RegisterForm
