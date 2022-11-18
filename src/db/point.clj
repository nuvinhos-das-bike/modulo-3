(ns db.point
  (:require [model.point :as model.point]
            [schema.core :as s]
            [datomic.client.api :as d]))

(s/defn get-all-points [conn] :- model.point/PointsWithBikes
  (->> (mapv first (d/q '[:find (pull ?e [[:point/id :as :id]
                                          [:point/name :as :name]
                                          [:point/capacity :as :capacity]
                                          {[:point/address :as :address] [[:address/id :as :id]
                                                             [:address/street :as :street]
                                                             [:address/number :as :number]
                                                             [:address/zip-code :as :zip-code]
                                                             [:address/address-line :as :address-line]]}
                                          {[:point/bikes :as :bikes] [[:bike/id :as :id]]}])
                          :where [?e :point/id]] (d/db conn)))
       ))

(s/defn get-point :- model.point/Point
  [id :- s/Keyword
   db]
  (get-in @db [:points id]))