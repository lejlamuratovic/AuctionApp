import { useCallback, useEffect, useState, useRef } from "react";

import "./style.scss";

const MultiRangeSlider = ({ min, max, minValue, maxValue, onChange }) => {
  const [minVal, setMinVal] = useState(minValue);
  const [maxVal, setMaxVal] = useState(maxValue);
  const minValRef = useRef(null);
  const maxValRef = useRef(null);
  const range = useRef(null);

  useEffect(() => {
    setMinVal(minValue);
  }, [minValue]);

  useEffect(() => {
    setMaxVal(maxValue);
  }, [maxValue]);

  const handleFinalChange = () => {
    onChange({ min: minVal, max: maxVal });
  };

  const handleMinChange = (e) => {
    setMinVal(Number(e.target.value));
  };

  const handleMaxChange = (e) => {
    setMaxVal(Number(e.target.value));
  };

  return (
    <div className="range-container">
      <input
        type="range"
        min={ min}
        max={ max}
        value={ minVal}
        ref={ minValRef}
        onChange={ handleMinChange }
        onMouseUp={ handleFinalChange } 
        onTouchEnd={ handleFinalChange } 
        className={ `thumb thumb--zindex-3 ${minVal > max - 100 ? "thumb--zindex-5" : ""}` }
        step= {0.1}
      />
      <input
        type="range"
        min={ min }
        max={ max }
        value={ maxVal }
        ref={ maxValRef }
        onChange={ handleMaxChange }
        onMouseUp={ handleFinalChange } 
        onTouchEnd={ handleFinalChange }
        className="thumb thumb--zindex-4"
        step={ 0.1 }
      />
      <div className="slider">
        <div className="slider__track" />
        <div ref={ range } className="slider__range" />
      </div>
    </div>
  );
};

export default MultiRangeSlider;
