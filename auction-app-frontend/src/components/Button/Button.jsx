import "./style.scss";

const Button = ({ label, iconSrc, disabled = false, variant = "border", onClickBehaviour }) => {
  const buttonClasses = `btn body-bold ${ disabled ? "disabled" : "" } ${
    variant === "filled" ? "filled" : ""
  }`;

  return (
    <button className={ buttonClasses } disabled={ disabled } onClick={ onClickBehaviour }>
      { label }
      { iconSrc && <img src={ iconSrc } alt="icon" className="btn-icon" /> }
    </button>
  );
};

export default Button;
