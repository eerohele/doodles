(ns app.core
  (:require [reagent.core :as reagent]))


(defonce progress (reagent/atom 42))


(defn slider
  [progress]
  [:input
   {:default-value @progress
    :type          "range"
    :step          1
    :min           0
    :max           100
    :on-change     #(reset! progress (.. % -target -value))}])


(defn progress-circle
  [radius progress]

  (let [center        (* radius 1.1)
        diameter      (* center 2)
        circumference (* 2 js/Math.PI radius)]

    [:svg
     {:width   diameter
      :height  diameter
      :viewBox (str "0 0" " " diameter " " diameter)}

     [:g
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
       {:y                  center
        :x                  center
        :font-size          (str (/ circumference 2) \%)
        :alignment-baseline "middle"
        :text-anchor        "middle"} @progress]]]))


(defn main-panel
  []
  (fn []
    [:main
     [progress-circle 100 progress]
     [slider progress]]))


(defn mount-root []
  (reagent/render [main-panel]
                  (.getElementById js/document "app")))


(defn main
  []
  (mount-root))
