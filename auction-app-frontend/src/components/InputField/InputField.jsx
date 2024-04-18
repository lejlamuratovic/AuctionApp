import "./style.scss"

const InputField = ({ ...props }) => {
    return (
        <div className="input-field">
            <label htmlFor={ props.id } className="body-semibold">{ props.label }</label>
            <input 
                type={ props.type } 
                placeholder={ props.placeholder } 
                id={ props.id }
                className={ props.isError ? 'error' : '' } 
            />
        </div>
    )
}

export default InputField
