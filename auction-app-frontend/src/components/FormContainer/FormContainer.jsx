import { FormProvider } from "react-hook-form";

import { InputField, Button } from "src/components";

import { BUTTON_VARIANTS } from "src/constants";

const FormContainer = ({ 
  formFields, 
  onSubmit, 
  buttonLabel,
  methods, 
  error, 
  buttonVariant = BUTTON_VARIANTS.FILLED, 
  buttonIcon, 
  children,
  onCancel, 
  cancelLabel,
  onBack,
  backLabel
}) => {
  return (
    <FormProvider { ...methods }>
      <form onSubmit={ methods.handleSubmit(onSubmit) }>
        { children ? children : formFields.map(field => ( // children is used to render custom fields 
          <InputField
              key={ field.name }
              name={ field.name }
              label={ field.label }
              type={ field.type }
              rules={ field.rules }
              step={ field.type === "number" ? field.step : null }
              options={ field.type === "select" ? field.options : null }
              className={ field.specialClass ? field.specialClass : ""}
              placeholder={ field.placeholder }
              onSelectChange={ field.onSelectChange }
          />
        )) }
        { error && 
          <div className="error-message-body body-semibold">
            { error }
          </div> 
        }
        <div className="button-container">
          { onCancel && <Button type="button" label={ cancelLabel } disabled={ true } onButtonClick={ onCancel }/> }
          <div className="btn-navigation">
            { onBack && <Button type="button" label={ backLabel } onButtonClick={ onBack } /> }
            <Button type="submit" variant={ buttonVariant } label={ buttonLabel } iconSrc={ buttonIcon }/>
          </div>
        </div>
      </form>
    </FormProvider>
  );
}

export default FormContainer;
