(ns model.bike
  (:require [schema.core :as s]))

(def canonical-bike
  {(s/optional-key :point) s/Keyword
   (s/optional-key :user)  s/Keyword})

(s/defschema Bike
  canonical-bike)

(s/defschema Bikes
  {s/Keyword Bike})