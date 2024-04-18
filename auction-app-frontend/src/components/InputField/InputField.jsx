import { FORM_DATA } from "src/constants"

import "./style.scss"

const InputField = () => {
    return (
        <div className="input-field">
            <label htmlFor={ FORM_DATA.id } className="body-semibold">{ FORM_DATA.label }</label>
            <input 
                type={ FORM_DATA.type } 
                placeholder={ FORM_DATA.placeholder } 
                id={ FORM_DATA.id }
                className={ FORM_DATA.isError ? 'error' : '' } 
            />
        </div>
    )
}

export default InputField
