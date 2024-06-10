import { useCallback, useEffect, useState, useRef } from "react";

import "./style.scss";

const MultiRangeSlider = ({ min, max, minValue, maxValue, onChange }) => {
  const [minVal, setMinVal] = useState(minValue);
  const [maxVal, setMaxVal] = useState(maxValue);
  const minValRef = useRef(null);
  const maxValRef = useRef(null);
  const range = useRef(null);

  const getPercent = useCallback(
    (value) => Math.round(((value - min) / (max - min)) * 100),
    [min, max]
  );

  useEffect(() => {
    setMinVal(minValue);
  }, [minValue]);

  useEffect(() => {
    setMaxVal(maxValue);
  }, [maxValue]);

  useEffect(() => {
    if (maxValRef.current) {
      const minPercent = getPercent(minVal);
      const maxPercent = getPercent(+maxValRef.current.value);

      if (range.current) {
        range.current.style.left = `${ minPercent }%`;
        range.current.style.width = `${ maxPercent - minPercent }%`;
      }
    }
  }, [minVal, getPercent]);

  useEffect(() => {
    if (minValRef.current) {
      const minPercent = getPercent(+minValRef.current.value);
      const maxPercent = getPercent(maxVal);

      if (range.current) {
        range.current.style.width = `${ maxPercent - minPercent }%`;
      }
    }
  }, [maxVal, getPercent]);

  useEffect(() => {
    onChange({ min: minVal, max: maxVal });
  }, [minVal, maxVal, onChange]);
  
  const onMinInputChange = (event) => {
    const value = Math.min(+event.target.value, maxVal - 1);

    setMinVal(value);
    event.target.value = value.toString();
  }

  const onMaxInputChange = (event) => {
    const value = Math.max(+event.target.value, minVal + 1);

    setMaxVal(value);
    event.target.value = value.toString();
  }

  return (
    <div className="range-container">
      <input
        type="range"
        min={ min }
        max={ max }
        value={ minVal }
        ref={ minValRef }
        onChange={ onMinInputChange }
        className={`thumb thumb--zindex-3 ${minVal > max - 100 ? "thumb--zindex-5" : ""}`}
      />
      <input
        type="range"
        min={ min }
        max={ max }
        value={ maxVal }
        ref={ maxValRef }
        onChange={ onMaxInputChange }
        className="thumb thumb--zindex-4"
      />

      <div className="slider">
        <div className="slider__track" />
        <div ref={ range } className="slider__range" />
      </div>
    </div>
  );
};

export default MultiRangeSlider;
