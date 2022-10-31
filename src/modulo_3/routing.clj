(ns modulo-3.routing
  (:require [io.pedestal.http.route :as route]
            [com.stuartsierra.component :as component]
            [modulo-3.interceptors :as i]))

(def routes
  (route/expand-routes
 
    #{["/bikes/:id-bike/users/:id-user" :patch [i/validate-user-exists i/authorize-user i/validate-bike i/handle-bike-request] :route-name :handle-bike-request]
      ["/bikes/:id-bike/points/:id-point" :patch [i/authorize-user i/validate-bike i/validate-user-has-bike i/validate-point i/handle-bike-devolution] :route-name :handle-bike-devolution]
      ["/users/:id-user/subscription" :patch [i/authorize-user i/handle-subscription] :route-name :handle-user-subscription]
      ["/points" :get i/all-points-interceptor :route-name :all-points]}))

(defrecord Routes []
  component/Lifecycle
  (start [this]
    (assoc this :routes routes))
  (stop [this]
    (dissoc this :routes))
  )

(defn new-routes []
  (->Routes))
