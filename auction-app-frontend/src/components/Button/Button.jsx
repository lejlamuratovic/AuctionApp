import "./style.scss";

const Button = ({ type, label, iconSrc, disabled = false, variant = "border", onButtonClick }) => {
  const buttonClasses = `btn body-bold ${ disabled ? "disabled" : "" } ${
    variant === "filled" ? "filled" : ""
  }`;

  return (
    <button className={ buttonClasses } disabled={ disabled } onClick={ onButtonClick } type={ type }>
      { label }
      { iconSrc && <img src={ iconSrc } alt="icon" className="btn-icon" /> }
    </button>
  );
};

export default Button;
