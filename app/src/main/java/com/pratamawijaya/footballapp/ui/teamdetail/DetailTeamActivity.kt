package com.pratamawijaya.footballapp.ui.teamdetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import com.pratamawijaya.footballapp.domain.Team
import com.squareup.picasso.Picasso
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.dip
import org.jetbrains.anko.imageView
import org.jetbrains.anko.info
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.scrollView
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout
import org.jetbrains.anko.wrapContent

class DetailTeamActivity : AppCompatActivity(), AnkoLogger {

    lateinit var imgTeam: ImageView
    lateinit var teamName: TextView
    lateinit var textDesc: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUI()

        val team = intent.getParcelableExtra<Team>("team")
        info("team name ${team.name}")

        textDesc.text = team.desc
        teamName.text = team.name
        Picasso.get().load(team.badge).into(imgTeam)
    }

    private fun setupUI() {
        scrollView {
            verticalLayout {
                imgTeam = imageView()
                        .lparams(width = dip(80), height = dip(80)) {
                            gravity = Gravity.CENTER_HORIZONTAL
                            topMargin = dip(16)
                            bottomMargin = dip(16)

                        }

                teamName = textView {

                }.lparams(width = wrapContent, height = wrapContent) {
                    gravity = Gravity.CENTER_HORIZONTAL
                    bottomMargin = dip(16)
                }

                textDesc = textView {
                    textSize = 18f
                }.lparams(width = matchParent, height = wrapContent) {
                    gravity = Gravity.CENTER_HORIZONTAL
                    leftMargin = dip(16)
                    rightMargin = dip(16)
                }
            }
        }
    }
}
