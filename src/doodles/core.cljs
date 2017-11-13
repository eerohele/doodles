(ns doodles.core
  (:require [doodles.progress-circle :as progress-circle]))


(defn main
  []
  (progress-circle/mount-root))
