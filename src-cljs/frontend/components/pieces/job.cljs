(ns frontend.components.pieces.job
  (:require [frontend.components.common :as common]
            [frontend.components.pieces.card :as card]
            [frontend.components.pieces.status :as status]
            [frontend.datetime :as datetime]
            [frontend.routes :as routes]
            [frontend.utils :refer-macros [component element html]]
            [frontend.utils.legacy :refer [build-legacy]]
            [om.next :as om-next :refer-macros [defui]]))

(defn- status-class [run-status]
  (case run-status
    (:job-run-status/waiting
     :job-run-status/not-running) :status-class/waiting
    :job-run-status/running :status-class/running
    :job-run-status/succeeded :status-class/succeeded
    (:job-run-status/failed :job-run-status/timed-out) :status-class/failed
    (:job-run-status/canceled
     :job-run-status/not-run) :status-class/stopped))

(defui ^:once Job
  static om-next/Ident
  (ident [this {:keys [job/id]}]
    [:job/by-id id])
  static om-next/IQuery
  (query [this]
    [:job/id
     :job/status
     :job/started-at
     :job/stopped-at
     :job/name
     :job/build
     {:job/required-jobs [:job/name]}
     {:job/run [:run/id]}])
  Object
  (render [this]
    (component
      (let [{:keys [job/status
                    job/started-at
                    job/stopped-at
                    job/required-jobs]
             {:keys [build/vcs-type
                     build/org
                     build/repo
                     build/number]
              :as build} :job/build
             job-name :job/name}
            (om-next/props this)]
        (card/basic
         (element :content
           (html
            [:div
             [:div.job-card-inner
              [:div.status-heading
               [:div.status-name
                [:span.job-status (status/icon (status-class status))]
                (if (nil? build)
                  [:span.job-name job-name]
                  [:a {:href
                     (routes/v1-build-path vcs-type
                                           org
                                           repo
                                           nil
                                           number)}
                 [:span.job-name job-name]])]]
              (when (seq required-jobs)
                [:div.requires
                 [:span.requires-heading "Requires"]
                 [:ul.requirements
                  (for [required-job required-jobs]
                    [:li.requirement (:job/name required-job)])]])
              [:div.metadata
               [:div.metadata-row.timing
                [:span.metadata-item.recent-time.start-time
                 [:i.material-icons "today"]
                 (if started-at
                   [:span {:title (str "Started: " (datetime/full-datetime started-at))}
                    (build-legacy common/updating-duration {:start started-at} {:opts {:formatter datetime/time-ago-abbreviated}})
                    [:span " ago"]]
                   "-")]
                [:span.metadata-item.recent-time.duration
                 [:i.material-icons "timer"]
                 (if stopped-at
                   [:span {:title (str "Duration: " (datetime/as-duration (- stopped-at started-at)))}
                    (build-legacy common/updating-duration {:start started-at
                                                            :stop stopped-at})]
                   "-")]]]]])))))))

(def job (om-next/factory Job {:keyfn :job/id}))
