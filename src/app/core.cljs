(ns app.core
  (:require [reagent.core :as reagent]))


(defonce progress (reagent/atom 42))


(defn target-val
  [event]
  (.. event -target -value))


(defn slider
  [progress]
  [:input
   {:default-value @progress
    :type          "range"
    :step          1
    :min           0
    :max           100
    :on-change     #(reset! progress (target-val %))}])


(defn progress-circle
  [radius progress]
  (let [center        (Math/round (* radius 1.1))
        diameter      (* center 2)
        circumference (* 2 js/Math.PI radius)]

    [:svg
     {:width   diameter
      :height  diameter
      :viewBox (str "0 0" " " diameter " " diameter)}

     [:g.progress-group
      [:circle.progress-meter
       {:cx    center
        :cy    center
        :r     radius
        :style {:fill "transparent"}}]

      [:circle.progress-value
       {:cx        center
        :cy        center
        :r         radius
        :transform (str "rotate(270, " center ", " center ")")
        :style     {:fill              "transparent"
                    :stroke-dasharray  circumference
                    :stroke-dashoffset (* circumference (- 1 (/ @progress 100)))}}]

      [:text.progress-text
       {:y                  "50%"
        :x                  "50%"
        :font-size          (str (/ radius 3) "px")
        :alignment-baseline "middle"
        :dominant-baseline  "middle"
        :text-anchor        "middle"} @progress]]]))


(defn main-panel
  []
  (fn []
    [:main
     [:h1 "Progress"]
     [progress-circle 100 progress]
     [slider progress]]))


(defn mount-root
  []
  (reagent/render [main-panel]
                  (.getElementById js/document "app")))


(defn main
  []
  (mount-root))
